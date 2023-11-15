package be.vinci.ipl.execution;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

  private ExecutionService service;

  public ExecutionController(ExecutionService service){
    this.service = service;
  }

}
