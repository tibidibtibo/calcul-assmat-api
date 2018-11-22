package fr.deboissieu.calculassmat.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;

public class IdentifiantUtils {

	public static List<String> convertToHexIds(List<ObjectId> objectsIds) {
		return new ArrayList<>(CollectionUtils.collect(objectsIds, IdentifiantUtils::convertToHexId));
	}

	public static String convertToHexId(ObjectId objectId) {
		return objectId.toHexString();
	}
}
