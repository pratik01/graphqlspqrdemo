package com.rnd.graphql.graphql.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.JoinType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd.graphql.graphql.models.User;

public class SelectQueryBuilder {

	static StringBuilder query = new StringBuilder("SELECT ");
	static List<Map<String, String[]>> fields = new ArrayList<Map<String, String[]>>();
	static List<Class<?>> cList = new ArrayList<>();
	static int index, recordIndex = 0;

	public static class Builder {

		String tableName;
		String parentAlias;

		public void init() {
			query = new StringBuilder("SELECT ");
			fields = new ArrayList<Map<String, String[]>>();
			cList = new ArrayList<>();
		}

		public Builder addParentField(String[] parentField, String alias) {
			init();
			Map<String, String[]> field = new HashMap<String, String[]>();
			field.put(alias, parentField);
			fields.add(field);
			parentField = prepend(parentField, alias);
			int index = 0;
			for (String str : parentField) {
				query.append(str);
				if (index < parentField.length - 1)
					query.append(",");
				index++;
			}

			return this;
		}

		public Builder addChildField(String alias, String[] childField) {
			Map<String, String[]> field = new HashMap<String, String[]>();
			field.put(alias, childField);
			fields.add(field);
			field.forEach((k, v) -> {
				for (String s : v) {
					query.append(",");
					query.append(k + "." + s);
				}
			});

			return this;
		}

		public Builder from(Class<?> clz, String alias) {
			this.tableName = clz.getSimpleName() + " " + alias;
			query.append(" FROM ");
			query.append(this.tableName);
			return this;
		}

		public Builder on(JoinType joinType, Class<?> clz, String leftAlias, String childField, String parentField,
				String rightAlias) {
			cList.add(clz);
			query.append(" ");
			query.append(joinType.name());
			query.append(" JOIN ");
			query.append(clz.getSimpleName());
			query.append(" ");
			query.append(leftAlias);
			query.append(" ON " + leftAlias + "." + childField + "=" + rightAlias + "." + parentField);
			return this;
		}

		public String build() {
			System.out.println(query.toString());
			return query.toString();
		}

		public void transformation(List<Object[]> list) {
			Class<?> clz = User.class;
			cList.add(0, clz);
			JSONArray jArray = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			try {
				String newJsonData = mapper.writeValueAsString(list);
				System.out.println(newJsonData);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			for (Object[] o : list) {
				JSONObject jObj = new JSONObject();
				index = 0;
				fields.forEach(field -> {
					recordIndex = 0;
					field.forEach((k, attributes) -> {
						System.out.println("pa = " + parentAlias);
						System.out.println("k = " + k);
						System.out.println("index == " + index);
						System.out.println(cList.get(index).getSimpleName());
						JSONObject entity = new JSONObject();
						for (String attribute : attributes) {
							String type = kindOf(attribute, cList.get(index));
							System.out.println(attribute + " " + type);
							System.out.println("val = " + o[recordIndex]);
							if (String.class.getSimpleName().equals(type)) {
								try {
									entity.put(attribute, o[recordIndex].toString());
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							if (Long.class.getSimpleName().equals(type)) {
								try {
									entity.put(attribute, Long.parseLong(o[recordIndex].toString()));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							if (Integer.class.getSimpleName().equals(type)) {
								try {
									entity.put(attribute, Long.parseLong(o[recordIndex].toString()));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							recordIndex++;
						}
						try {
							jObj.put(cList.get(index).getSimpleName(), entity);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						jArray.put(jObj);
						index++;

						System.out.println("index = " + index);
					});
				});
			}

			System.out.println(jArray.toString());

		}

		public String kindOf(String selection, Class<?> clz) {
			try {
				java.lang.reflect.Field field = null;
				try {
					field = clz.getSuperclass().getDeclaredField(selection);
				} catch (Exception e) {

				}
				if (field == null) {
					field = clz.getDeclaredField(selection);
					if (field != null) {
						return field.getType().getSimpleName();
					}
				} else {
					return field.getType().getSimpleName();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		private String[] prepend(String[] input, String prepend) {
			String[] output = new String[input.length];
			for (int index = 0; index < input.length; index++) {
				output[index] = prepend + "." + input[index];
			}
			return output;
		}
	}

}