package diplom;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 20.03.14
 */
public class MatlabService {
    private static MatlabService instance;

    private MatlabProxy proxy;
    private MatlabTypeConverter typeConverter;

    private MatlabService() {
    }

    public static MatlabService getInstance() throws MatlabConnectionException {
        if (instance == null) {
            instance = new MatlabService();
        }

        return instance;
    }

    public void connect() throws MatlabConnectionException {
        if (proxy == null) {
            MatlabProxyFactory factory = new MatlabProxyFactory();
            proxy = factory.getProxy();
            typeConverter = new MatlabTypeConverter(proxy);
        }
    }

    public void disconnect() {
        proxy.disconnect();
    }

    public void initGlobalVariables(double r0) throws MatlabInvocationException {
        proxy.eval(String.format(
                "global ax ay az bx by bz;\n" +
                        "global r0;\n" +
                        "global Ntop;\n" +
                        "global X;\n" +
                        "r0=%s;\n" +
                        "Ntop=17;", r0));
    }

    public Conformation setInitialConformation(String fileName, int initialSize) throws MatlabInvocationException {
        //proxy.eval("Xsimp = fscanf(fopen('C:\\Users\\Иришка\\Documents\\MATLAB\\Vigual3\\X6R81.txt', 'r'),'%g',[3,7]);");
        proxy.eval("Xsimp = fscanf(fopen('" + fileName + "', 'r'),'%g',[3," + initialSize + "]);");
        proxy.eval("Xsimp = Xsimp';");

        double[][] positions = typeConverter.getNumericArray("Xsimp").getRealArray2D();
        proxy.eval("U = UmorseResult(Xsimp, " + positions.length + ", r0);");
        double energy = ((double[]) proxy.getVariable("U"))[0];
        return new Conformation(positions.length, energy, positions);
    }

    public List<Conformation> getNextConformations(Conformation baseConformation) throws MatlabInvocationException {
        typeConverter.setNumericArray("Xsimp", new MatlabNumericArray(baseConformation.getPositions(), null));

        int newSize = baseConformation.getSize() + 1;

        proxy.eval("[TRf,Xres]=DIS_KLAST(Xsimp);");

        MatlabNumericArray positionsArray = typeConverter.getNumericArray("Xres");
        double[][] allPositions = positionsArray.getRealArray2D();

        MatlabNumericArray candidatesArray = typeConverter.getNumericArray("TRf");
        double[][] candidates = candidatesArray.getRealArray2D();

        List<Conformation> resultConformations = new ArrayList<Conformation>(candidates.length);
        for (int i = 0; i < candidates.length; i++) {
            double energy = candidates[i][3];
            double[][] positions = Arrays.copyOfRange(allPositions, i * newSize, (i + 1) * newSize);

            Conformation conformation = new Conformation(newSize, energy, positions);
            resultConformations.add(conformation);
        }
        return resultConformations;
    }

    public void optimizeAtomPositions(Conformation conformation) throws MatlabInvocationException {
        typeConverter.setNumericArray("Xsimp", new MatlabNumericArray(conformation.getPositions(), null));

/*        proxy.eval(String.format("[XR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, %s, %s),Xsimp, " +
                "optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));", conformation.getSize(), Settings.R0));*/
        proxy.eval(String.format("[XR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, %s, r0),Xsimp, " +
                "optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));", conformation.getSize()));


        MatlabNumericArray positionsArray = typeConverter.getNumericArray("XR");
        double[][] positions = positionsArray.getRealArray2D();

        double energy = ((double[]) proxy.getVariable("FR2"))[0];

        conformation.setPositions(positions);
        conformation.setEnergy(energy);
    }
}
