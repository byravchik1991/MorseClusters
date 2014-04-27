package diplom;

import diplom.Conformation;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 22.03.14
 */
public class ConformationHelper {

    public static void link(Conformation predecessor, Conformation successor) {
        predecessor.getSuccessors().add(successor);
        successor.getPredecessors().add(predecessor);
    }

    public static void unlink(Conformation predecessor, Conformation successor) {
        predecessor.getSuccessors().remove(predecessor);
        successor.getPredecessors().remove(successor);
    }

    public static Set<Conformation> collectAllConformations(ConformationTree conformationTree) {
        Set<Conformation> resultConformations = new HashSet<Conformation>();
        Queue<Conformation> queue = new LinkedList<Conformation>();

        queue.add(conformationTree.getRoot());

        while (!queue.isEmpty()) {
            final Conformation conformation = queue.poll();

            resultConformations.add(conformation);

            queue.addAll(conformation.getSuccessors());
        }

        return resultConformations;
    }

    public static Conformation findBestConformation(Collection<Conformation> conformations) {
        return Collections.min(conformations, new Comparator<Conformation>() {
            @Override
            public int compare(Conformation o1, Conformation o2) {
                return ((Double) (o1.getEnergy())).compareTo(o2.getEnergy());
            }
        });
    }

    public static Set<Conformation> collectAllConformationsBySize(ConformationTree conformationTree, int size) {
        Set<Conformation> resultConformations = new HashSet<Conformation>();
        Queue<Conformation> queue = new LinkedList<Conformation>();

        queue.add(conformationTree.getRoot());

        while (!queue.isEmpty()) {
            final Conformation conformation = queue.poll();

            if (conformation.getSize() == size) {
                resultConformations.add(conformation);
            } else {
                queue.addAll(conformation.getSuccessors());
            }
        }

        return resultConformations;
    }
}
