package org.matsim.delhi;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.operation.Transformation;

import java.io.File;
import java.util.Collection;


public class ShapefileFeatures {
    public static void main(String[] args) throws Exception {
        Collection<SimpleFeature>allFeatures = ShapeFileReader.getAllFeatures("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/delhi.shp");

        for(SimpleFeature feature : allFeatures){
            System.out.println(feature);
        }
        CoordinateTransformation crs = TransformationFactory.getCoordinateTransformation("WGS84", "EPSG:7760");
        for(SimpleFeature feature : allFeatures){
            System.out.println(feature);
        }
    }
}
