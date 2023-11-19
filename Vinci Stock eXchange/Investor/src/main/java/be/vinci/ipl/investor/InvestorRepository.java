package be.vinci.ipl.investor;

import be.vinci.ipl.investor.model.InvestorData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends CrudRepository<InvestorData, String> {
}
