package com.stecnology.hrworker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stecnology.hrworker.entities.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
