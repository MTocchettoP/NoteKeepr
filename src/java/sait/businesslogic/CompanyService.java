/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.businesslogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sait.dataaccess.CompanyDB;
import sait.dataaccess.NotesDBException;
import sait.domainmodel.Company;

/**
 *
 * @author 733196
 */
public class CompanyService {

    private final CompanyDB companyDB;

    public CompanyService() {
        companyDB = new CompanyDB();
    }

    public List<Company> getAll() throws Exception {
        return new ArrayList<>(companyDB.getAll());
    }

    public Company get(String company) throws NotesDBException {
        return companyDB.get(company);
    }

    public boolean update(Company company) throws Exception {
        return (companyDB.update(company) == 1);
    }

    public boolean add(String name) throws IOException, Exception {
        Company company = new Company();
        company.setCompanyName(name);

        ArrayList<Company> companies = new ArrayList<Company>(this.getAll());
        for (Company comp : companies) {
            if (comp.getCompanyName().equals(name)) 
                return false;//company already exist                 
        }
         return (companyDB.add(company) == 1);
    }
}
