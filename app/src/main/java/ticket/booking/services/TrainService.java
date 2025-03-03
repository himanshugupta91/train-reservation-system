package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {

    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAIN_DB_PATH = "src/main/resources/database/trains.json";

    public TrainService() throws IOException{
        File train = new File(TRAIN_DB_PATH);
        trainList = objectMapper.readValue(train, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source,String destination){
        return trainList.stream().filter(train -> validTrain(train,source, destination)).collect(Collectors.toList());
    }

    public void addTrain(Train newTrain){
        // checking if a train with the same trainId already exists
        Optional<Train> existingTrain = trainList.stream().filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();
        if(existingTrain.isPresent()){
            // if a train with the same trainId exists, update it instead of adding a new one
            updateTrain(newTrain);
        } else {
            // otherwise add the new train to the list
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain){
        // find the index of the train with the same trainId
        OptionalInt index = IntStream.range(0, trainList.size()).filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId())).findFirst();
        if(index.isPresent()){
            // if found replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        } else {
            // if not found treat it as adding a new train
            addTrain(updatedTrain);
        }
    }

    private void saveTrainListToFile(){
        try{
            objectMapper.writeValue(new File(TRAIN_DB_PATH),trainList);
        } catch (IOException e) {
            e.printStackTrace(); // handle the exception based on your application requirement
        }
    }

    private boolean validTrain(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1;
    }
}
