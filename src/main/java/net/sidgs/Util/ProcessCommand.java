package net.sidgs.Util;

import net.sidgs.Error.InvalidCommandException;
import net.sidgs.Model.Connection;
import net.sidgs.Model.Departure;
import net.sidgs.Model.Node;

import static net.sidgs.Util.Data.conncetions;
import static net.sidgs.Util.Data.departures;
import static net.sidgs.Util.Data.nodes;

/**
 * Created by annapureddy on 1/23/2018.
 * it takes sample data from console and based upon # sections name it separeate and store it in collections
 * if sample data does't  match requirment it will say invalid command
 * it adds data to departures ,connections and nodes collections
 */
public class ProcessCommand {
    //take input and spliting  sections based upon # section
    public boolean  execute(String command) throws InvalidCommandException {
        if(command==null){
            return false;
        }
        else {

            command = command.toLowerCase();
            String[] tokens = command.split(" ");
            if (tokens.length < 3) throw new InvalidCommandException("Invalid Command : Has invalid number of tokens");
            else {
                if (command.contains("#")) {
                    if (command.contains("conveyor"))
                        StatusFlag.STATUS = StatusFlag.status.CONVSYSTEM;
                    else if (command.contains("departures"))
                        StatusFlag.STATUS = StatusFlag.status.DEPARTURES;
                    else if (command.contains("bags"))
                        StatusFlag.STATUS = StatusFlag.status.BAGS;
                    else throw new InvalidCommandException("");
                } else {
                    if (StatusFlag.STATUS == StatusFlag.status.CONVSYSTEM) {
                        if (tokens.length != 3)
                            throw new InvalidCommandException("Inavlid command : try <Node 1> <Node 2> <travel_time>");
                        insertNode(tokens[0]);
                        insertNode(tokens[1]);
                        createConnection(tokens[0], tokens[1], tokens[2]);
//                    printNodesAndConnections();
                    } else if (StatusFlag.STATUS == StatusFlag.status.DEPARTURES) {
                        if (tokens.length != 4)
                            throw new InvalidCommandException("Inavlid command : try <flight_id> <flight_gate> <destination> <flight_time>");
                        insertDeparture(tokens);

                    } else {
                        if (tokens.length != 3)
                            throw new InvalidCommandException("Inavlid command : try <bag_number> <entry_point> <flight_id>");
                        PathUtil pathUtil = new PathUtil();
                        pathUtil.findBestPath(tokens[0], tokens[1], tokens[2]);
                    }

                }


            }

        }
        return true;
    }

    /**
     * Adding to Departures collections
     *
     * @param tokens
     */
    private void insertDeparture(String[] tokens) {
        departures.add(new Departure(tokens[0], tokens[1], tokens[2], tokens[3]));
    }

    /**
     * creates connections and store it collections
     * validating nodes
     *
     * @param node1
     * @param node2
     * @param time
     * @throws InvalidCommandException
     */
    private void createConnection(String node1, String node2, String time) throws InvalidCommandException {
        if (node1.equals(node2)) throw new InvalidCommandException("Invalid command: Connecting the two same nodes");
        conncetions.add(new Connection(node1, node2, Integer.parseInt(time)));
    }

    private void insertNode(String name) {
        nodes.add(new Node(name));

    }

    void printNodesAndConnections() {
        for (Connection connection : conncetions)
            System.out.println(connection);
        for (Node node : nodes)
            System.out.println(node);
    }
}
