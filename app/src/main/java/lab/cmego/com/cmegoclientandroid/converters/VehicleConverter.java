package lab.cmego.com.cmegoclientandroid.converters;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.Vehicle.EmbeddedBeaconIdentifier;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.PlateIdentifier;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.Vehicle;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.VehicleIdentifier;
import lab.cmego.com.cmegoclientandroid.vehicle.VehicleContract;
import lab.cmego.com.cmegoclientandroid.vehicle.VehicleIdentifierContract;

/**
 * Created by Amit Ishai on 10/12/2017.
 */

public class VehicleConverter {

    public static Vehicle convert(VehicleContract contract){

        Vehicle retval = new Vehicle()
                .setId(contract.getId())
                .setType(convertType(contract.getType()));

        List<VehicleIdentifierContract> identifierContracts = contract.getVehicleIdentifiers();

        if(identifierContracts == null || identifierContracts.size() == 0){
            return retval;
        }

        List<VehicleIdentifier> identifiers = new ArrayList<>();

        for(VehicleIdentifierContract identifierContract : identifierContracts){
            VehicleIdentifier vehicleIdentifier = convertIdentifier(identifierContract);
            identifiers.add(vehicleIdentifier);
        }

        retval.setVehicleIdentifiers(identifiers);
        return retval;
    }

    private static VehicleIdentifier convertIdentifier(VehicleIdentifierContract contract) {

        switch (contract.getType()){
            case PLATE:
                return new PlateIdentifier(contract.getNumber(), contract.getLocale());
            case EMBEDDED_BEACON:
                return new EmbeddedBeaconIdentifier(contract.getMacAddress(), contract.getName());
        }

        return null;
    }

    private static Vehicle.Type convertType(VehicleContract.Type type) {

        if(type == null){
            return Vehicle.Type.SEDAN;
        }

        switch (type){
            case BUS:
                return Vehicle.Type.BUS;
            case HOT_AIR_BALLOON:
                return Vehicle.Type.HOT_AIR_BALLOON;
            case MINIVAN:
                return Vehicle.Type.MINIVAN;
            case MOTORCYCLE:
                return Vehicle.Type.MOTORCYCLE;
            case PLANE:
                return Vehicle.Type.PLANE;
            case SEDAN:
                return Vehicle.Type.SEDAN;
            case SPORTS_CAR:
                return Vehicle.Type.SPORTS_CAR;
            case SUV:
                return Vehicle.Type.SUV;
            case TRAIN:
                return Vehicle.Type.TRAIN;
            default:
                return Vehicle.Type.SEDAN;
        }
    }

}
