package Service;

import Domain.Produs;
import Repo.Repository;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    private final Repository<Produs> produsRepository;

    public Service(Repository<Produs> masinaRepository){
        this.produsRepository = masinaRepository;
    }

    public Repository<Produs> getProdusRepository(){
        return produsRepository;
    }


    public void addProdus(Produs produs){
        produsRepository.add(produs);
    }

    public void updateProdus(Produs produs){
        produsRepository.update(produs);
    }

    public void deleteProdus(int id){
        produsRepository.delete(id);
    }

    public java.util.List<Produs> finAllProdus(){
        return produsRepository.findAll();
    }

    public Produs findIDProdus(int id){
        return produsRepository.findID(id);
    }

    public int findNextId(){
        int maxId = 0;
        for (Produs produs : produsRepository.findAll()){
            if (produs.getId() > maxId){
                maxId = produs.getId();
            }
        }
        return maxId + 1;
    }

    public void schimbaCantitatea(){

    }
}
