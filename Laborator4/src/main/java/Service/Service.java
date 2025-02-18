package Service;

import Domain.Inchiriere;
import Domain.Masina;
import Repo.Repository;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    private final Repository<Masina> masinaRepository;
    private final Repository<Inchiriere> inchiriereRepository;

    public Service(Repository<Masina> masinaRepository, Repository<Inchiriere> inchiriereRepository){
        this.masinaRepository = masinaRepository;
        this.inchiriereRepository = inchiriereRepository;
    }

    public Repository<Masina> getMasinaRepository(){
        return masinaRepository;
    }

    public Repository<Inchiriere> getInchiriereRepository(){
        return inchiriereRepository;
    }

    public void addMasina(Masina masina){
        masinaRepository.add(masina);
    }

    public void addInchiriere(Inchiriere inchiriere){
        inchiriereRepository.add(inchiriere);
        System.out.println("Inchiriere adaugata: " + inchiriere);
    }

    public void updateMasina(Masina masina){
        masinaRepository.update(masina);
    }

    public void updateInchiriere(Inchiriere inchiriere){
        inchiriereRepository.update(inchiriere);
    }

    public void deleteMasina(int id){
        masinaRepository.delete(id);
    }

    public void deleteInchiriere(int id){
        inchiriereRepository.delete(id);
    }

    public java.util.List<Masina> findAllMasina(){
        return masinaRepository.findAll();
    }

    public java.util.List<Inchiriere> findAllInchiriere(){
        return inchiriereRepository.findAll();
    }

    public Masina findIDMasina(int id){
        return masinaRepository.findID(id);
    }

    public Inchiriere findIDInchiriere(int id){
        return inchiriereRepository.findID(id);
    }

    public Map<String, Integer> nrInchirieriMasini() {
        // Map pentru stocarea numărului de inchirieri per masina
        Map<String, Integer> InchirieriMasini = new HashMap<>();
        for (Masina masina : masinaRepository.findAll()) {
            for(Inchiriere inchiriere: inchiriereRepository.findAll()){
                if(inchiriere.getMasina().getId() == masina.getId())
                    InchirieriMasini.put(masina.getId()+ " " + masina.getMarca() + " " + masina.getModel(), InchirieriMasini.getOrDefault(masina.getId()+ " " +masina.getMarca() + " " + masina.getModel(), 0) + 1);
            }
        }
        // Sortăm după numărul de inchirieri, descrescător
        return InchirieriMasini.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> nrInchirieriLuna() {
        // Map pentru stocarea numărului de inchirieri per luna
        Map<String, Integer> InchirieriMasini = new HashMap<>();
        for (Inchiriere inchiriere : inchiriereRepository.findAll()) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String lunaInchiriere = monthFormat.format(inchiriere.getData_inceput());
            // Actualizăm numărul de inchirieri pentru luna respectivă
            InchirieriMasini.put(lunaInchiriere, InchirieriMasini.getOrDefault(lunaInchiriere, 0) + 1);
        }
        // Sortăm după numărul de inchirieri, descrescător
        return InchirieriMasini.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> nrZileInchiriate() {
        // Map pentru stocarea numărului total de zile per mașină
        Map<String, Integer> zileInchiriatePerMasina = new HashMap<>();

        // Iterăm prin toate închirierile
        for (Inchiriere inchiriere : inchiriereRepository.findAll()) {
            // Calculăm numărul total de zile dintre data de început și data de sfârșit
            long zile = ChronoUnit.DAYS.between(
                    inchiriere.getData_inceput().toLocalDate(),
                    inchiriere.getData_sfarsit().toLocalDate()
            );

            // Creăm cheia descriptivă pentru mașină
            String masinaKey = inchiriere.getMasina().getMarca() + " " +
                    inchiriere.getMasina().getModel() + " (ID: " + inchiriere.getMasina().getId() + ")";

            // Adăugăm sau actualizăm numărul de zile în map
            zileInchiriatePerMasina.put(masinaKey,
                    zileInchiriatePerMasina.getOrDefault(masinaKey, 0) + (int) zile);
        }

        // Sortăm rezultatele după numărul de zile, descrescător
        return zileInchiriatePerMasina.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // În caz de conflict, păstrăm valoarea veche (nu ar trebui să fie cazul aici)
                        LinkedHashMap::new // Păstrăm ordinea descrescătoare
                ));
    }


}
