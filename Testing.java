package osm4maps;

import java.io.FileInputStream;
import java.io.InputStream;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.pbf.seq.PbfIterator;

public class Testing {
    public static void main(String[] args) {
        try {
            String filePath = "src/main/java/osm4maps/data/alberta-latest.osm";
            InputStream input = new FileInputStream(filePath);
            OsmIterator iterator = new PbfIterator(input, false);
            String targetPostalCode = "T3A2S1";

            while (iterator.hasNext()) {
                OsmEntity entity = iterator.next().getEntity();

                // Check for postal code in tags of Node, Way, and Relation
                if (entity instanceof OsmNode || entity instanceof OsmWay || entity instanceof OsmRelation) {
                    for (int i = 0; i < entity.getNumberOfTags(); i++) {
                        OsmTag tag = entity.getTag(i);
                        if ("addr:postcode".equals(tag.getKey()) && targetPostalCode.equals(tag.getValue())) {
                            System.out.println("Found address with postal code " + targetPostalCode + ":");
                            System.out.println("Entity ID: " + entity.getId());
                            // Print other details based on entity type
                            for (int j = 0; j < entity.getNumberOfTags(); j++) {
                                tag = entity.getTag(j);
                                System.out.println("Tag: " + tag.getKey() + " = " + tag.getValue());
                            }
                            break;
                        }
                    }
                }
            }

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
