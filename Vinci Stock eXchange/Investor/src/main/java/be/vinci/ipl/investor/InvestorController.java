package be.vinci.ipl.investor;
import be.vinci.ipl.investor.exceptions.BadRequestException;
import be.vinci.ipl.investor.exceptions.NotFoundException;
import be.vinci.ipl.investor.model.InvestorData;
import be.vinci.ipl.investor.model.InvestorWithPassword;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestorController {
  private InvestorService service;
  public InvestorController(InvestorService service){
    this.service = service;
  }

  @GetMapping("/investor/{username}")
  public ResponseEntity<InvestorData> readOne(@PathVariable String username){
    InvestorData investorData = service.readOne(username);
    if (investorData == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(investorData, HttpStatus.OK);
  }
  @PostMapping("/investor/{username}")
  public ResponseEntity<Void> createOne(@PathVariable String username, @RequestBody
  InvestorWithPassword investorWithPassword){
    if (!Objects.equals(investorWithPassword.getInvestorData().getUsername(), username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (investorWithPassword.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    boolean created = service.createOne(investorWithPassword);
    if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/investor/{username}")
  public ResponseEntity<Void> updateOne(@PathVariable String username, @RequestBody InvestorData investorData) {
    if (!Objects.equals(investorData.getUsername(), username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (investorData.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    boolean found = service.updateOne(investorData);

    if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/investor/{username}")
  public ResponseEntity<Void> deleteOne(@PathVariable String username) {
    try{
      service.deleteOne(username);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotFoundException e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
