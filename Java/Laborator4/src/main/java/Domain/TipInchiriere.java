package Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TipInchiriere implements I_TipObiect<Inchiriere> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Inchiriere createObiect(String line) {
        String[] parts = line.split(",");

        // Parsing data_inceput
        Date dataInceput;
        try {
            dataInceput = DATE_FORMAT.parse(parts[2].trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format for data_inceput: " + parts[2], e);
        }

        // Parsing data_sfarsit
        Date dataSfarsit;
        try {
            dataSfarsit = DATE_FORMAT.parse(parts[3].trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format for data_sfarsit: " + parts[3], e);
        }

        // Converting util.Date to sql.Date
        java.sql.Date sqlDataInceput = new java.sql.Date(dataInceput.getTime());
        java.sql.Date sqlDataSfarsit = new java.sql.Date(dataSfarsit.getTime());

        // Parsing Masina object
        String[] masinaDetails = parts[1].trim().split(" ");
        int masinaId = Integer.parseInt(masinaDetails[0].trim());
        String marca = masinaDetails[1].trim();
        String model = masinaDetails[2].trim();
        Masina masina = new Masina(masinaId, marca, model);

        // Return the new Inchiriere object
        int inchiriereId = Integer.parseInt(parts[0].trim());
        return new Inchiriere(inchiriereId, masina, sqlDataInceput, sqlDataSfarsit);
    }
}
