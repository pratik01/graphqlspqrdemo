package com.rnd.graphql.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rnd.graphql.graphql.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
