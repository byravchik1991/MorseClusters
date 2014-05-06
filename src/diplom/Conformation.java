package diplom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 19.03.14
 */
public class Conformation {
    private int id;
    private int size;
    private double energy;
    private double[][] positions;

    private List<Conformation> predecessors = new ArrayList<Conformation>();
    private List<Conformation> successors = new ArrayList<Conformation>();

    private boolean isBestConformation = false;

    public Conformation(int size, double energy, double[][] positions) {
        this.size = size;
        this.energy = energy;
        this.positions = positions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double[][] getPositions() {
        return positions;
    }

    public void setPositions(double[][] positions) {
        this.positions = positions;
    }

    public List<Conformation> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<Conformation> predecessors) {
        this.predecessors = predecessors;
    }

    public List<Conformation> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<Conformation> successors) {
        this.successors = successors;
    }

    public boolean isBestConformation() {
        return isBestConformation;
    }

    public void setBestConformation(boolean bestConformation) {
        isBestConformation = bestConformation;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder()
                .append("N = " + size).append("\n")
                .append("E = " + energy).append("\n");

        stringBuilder.append("{\n");
        for (int i = 0; i < positions.length; i++) {
            stringBuilder.append("\t[");
            stringBuilder.append(Arrays.toString(positions[i]));
/*            for (int j = 0; j < positions[i].length; j++) {
                stringBuilder.append(*//*String.format("%1$,.5f", *//*positions[i][j] + ", "*//*)*//*);
            }*/
            stringBuilder.append("]\n");
        }
        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return positions.equals(((Conformation) obj).getPositions());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(positions);
    }
}
