package be.vinci.ipl.execution;

import be.vinci.ipl.execution.repositories.ExecutionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

  private final ExecutionRepository repository;

  public ExecutionService(ExecutionRepository repository){
    this.repository = repository;
  }

}
