package org.example.evotingsystem.repositories.DbInitializer;

import jakarta.annotation.PostConstruct;
import org.example.evotingsystem.models.Admin;
import org.example.evotingsystem.models.Voter;
import org.example.evotingsystem.repositories.AdminRepository;
import org.example.evotingsystem.repositories.VoterRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DbInitializer implements Runnable {

    private final VoterRepository voterRepository;
    private final AdminRepository adminRepository;



    public DbInitializer(VoterRepository voterRepository, AdminRepository adminRepository) {
        this.voterRepository = voterRepository;
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    @Override
    public void run() {
        if (voterRepository.count() == 0) {

            Voter voter2 = new Voter();
            voter2.setFirstName("Alisa");
            voter2.setLastName("Grajqevci");
            voter2.setBirthdate(LocalDate.of(2006, 1, 16));
            voter2.setPersonalNumber("0011223344");
            voter2.setEmail("alisa.grajqevci@example.com");
            voterRepository.save(voter2);

            Voter voter1 = new Voter();
            voter1.setFirstName("Arsona");
            voter1.setLastName("Mehmeti");
            voter1.setBirthdate(LocalDate.of(2005, 10, 31));
            voter1.setPersonalNumber("1234567890");
            voter1.setEmail("arsona.mehmeti@example.com");
            voterRepository.save(voter1);


            Voter voter3 = new Voter();
            voter3.setFirstName("Yllka");
            voter3.setLastName("Kabashi");
            voter3.setBirthdate(LocalDate.of(2005, 1, 18));
            voter3.setPersonalNumber("1111111111");
            voter3.setEmail("yllka.kabashi@example.com");
            voterRepository.save(voter3);

            Voter voter5 = new Voter();
            voter5.setFirstName("Alma");
            voter5.setLastName("Grajqevci");
            voter5.setBirthdate(LocalDate.of(2001, 5, 20));
            voter5.setPersonalNumber("3333333333");
            voter5.setEmail("alma.grajqevci@example.com");
            voterRepository.save(voter5);

            Voter voter6 = new Voter();
            voter6.setFirstName("Alba");
            voter6.setLastName("Grajqevci");
            voter6.setBirthdate(LocalDate.of(2001, 5, 20));
            voter6.setPersonalNumber("0000000000");
            voter6.setEmail("alba.grajqevci@example.com");
            voterRepository.save(voter6);


            Voter voter7 = new Voter();
            voter7.setFirstName("Albin");
            voter7.setLastName("Grajqevci");
            voter7.setBirthdate(LocalDate.of(1999, 8, 29));
            voter7.setPersonalNumber("4444444444");
            voter7.setEmail("albin.grajqevci@example.com");
            voterRepository.save(voter7);

            Voter voter4 = new Voter();
            voter4.setFirstName("Arita");
            voter4.setLastName("Mehmeti");
            voter4.setBirthdate(LocalDate.of(2007, 8, 10));
            voter4.setPersonalNumber("2222222222");
            voter4.setEmail("arita.mehmeti@example.com");
            voterRepository.save(voter4);

            Voter voter8 = new Voter();
            voter8.setFirstName("Majlinda");
            voter8.setLastName("Kabashi");
            voter8.setBirthdate(LocalDate.of(2006, 5, 20));
            voter8.setPersonalNumber("5555555555");
            voter8.setEmail("majlinda.kabashi@example.com");
            voterRepository.save(voter8);

            Voter voter9 = new Voter();
            voter9.setFirstName("Ervina");
            voter9.setLastName("Behrami");
            voter9.setBirthdate(LocalDate.of(2003, 10, 14));
            voter9.setPersonalNumber("6666666666");
            voter9.setEmail("ervina.behrami@example.com");
            voterRepository.save(voter9);


            Voter voter10 = new Voter();
            voter10.setFirstName("Elsa");
            voter10.setLastName("Berisha");
            voter10.setBirthdate(LocalDate.of(2005, 12, 6));
            voter10.setPersonalNumber("7777777777");
            voter10.setEmail("engjell.dedinca@example.com");
            voterRepository.save(voter10);


            Voter voter11 = new Voter();
            voter11.setFirstName("Medina");
            voter11.setLastName("Kutleshi");
            voter11.setBirthdate(LocalDate.of(2004, 1, 18));
            voter11.setPersonalNumber("8888888888");
            voter11.setEmail("medina.kutleshi@example.com");
            voterRepository.save(voter11);

            Voter voter12 = new Voter();
            voter12.setFirstName("Someja");
            voter12.setLastName("Nuhiu");
            voter12.setBirthdate(LocalDate.of(2004, 8, 12));
            voter12.setPersonalNumber("1231231231");
            voter12.setEmail("someja.nuhiu@example.com");
            voterRepository.save(voter12);

            Voter voter13 = new Voter();
            voter13.setFirstName("Bora");
            voter13.setLastName("Celhaka");
            voter13.setBirthdate(LocalDate.of(2001, 9, 25));
            voter13.setPersonalNumber("1122334455");
            voter13.setEmail("gyltene.jusufi@example.com");
            voterRepository.save(voter13);
        }

        if (adminRepository.count() == 0) {

            Admin admin1 = new Admin();
            admin1.setUsername("alisa.grajqevci");
            admin1.setPassword("Admin123");
            admin1.setFirstName("Alisa");
            admin1.setLastName("Grajqevci");
            admin1.setEmail("alisa.grajqevci@example.com");
            adminRepository.save(admin1);

            Admin admin2 = new Admin();
            admin2.setUsername("arsona.mehmeti");
            admin2.setPassword("Admin123");
            admin2.setFirstName("Arsona");
            admin2.setLastName("Mehmeti");
            admin2.setEmail("arsona.mehmeti@example.com");
            adminRepository.save(admin2);

        }
    }
}
