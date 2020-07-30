package com.bluewood.springwebdemo.utils;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.processing.Operations;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.util.factory.Hints;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import javax.media.jai.PlanarImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 地图坐标系转换工具
 *
 * @author liming
 * @date 2020/7/23 13:56
 */
public class GeotoolsUtil {


    public static void main(String[] args) throws IOException, FactoryException, TransformException {
        transfor();

//        getAltitude(null);
    }

    public static void transfor() throws IOException, FactoryException {
        File file = new File("C:\\Users\\robu\\Desktop\\坐标转换\\83026_9e9_001.tif");
        String image_4326 = "C:\\Users\\robu\\Desktop\\坐标转换\\83026_9e9_001_4326.tif";
        if (file.exists()) {
            GeoTiffReader br = new GeoTiffReader(file);
            GridCoverage2D old2D = br.read(null);
//            Reader br = new Reader();
//            GridCoverage2D old2D = br.getGridCoverage2D(file);
            final CoordinateReferenceSystem WGS = CRS.decode("EPSG:4326");
            final CoordinateReferenceSystem sourceCRS = old2D.getCoordinateReferenceSystem();
            System.out.println(String.format("源坐标系为: %s", sourceCRS.getName()));
            GridCoverage2D new2D = (GridCoverage2D) Operations.DEFAULT.resample(old2D, WGS);
            System.err.println(String.format("目标坐标系为: %s", new2D.getCoordinateReferenceSystem().getName()));

            System.out.println("begin write file " + new Date().getTime());
            GeoTiffWriter writer = new GeoTiffWriter(new File(image_4326));
            writer.write(new2D, null);
            writer.dispose();
            System.out.println("write file done " + new Date().getTime());
        }
    }


    public static GeoTiffReader getImageReader(String iamge) {
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));

        try {
            File file = new File(iamge);
            GeoTiffReader tmpTif = new GeoTiffReader(file, tiffHints);
            return tmpTif;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void getAltitude(List<String> strPoints) throws IOException, TransformException, TransformException {

        String demPath = "C:\\Users\\robu\\Desktop\\坐标转换\\83026_9e9_001.tif";
        // String demPath = "F:/Data/yanta/YanTaDOM.tif";
        File file = new File(demPath);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        // 默认坐标系EPSG:3857
        //tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, CRS.decode("EPSG:4326")));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));

        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        RenderedImage sourceImage = coverage.getRenderedImage();

        PlanarImage planarImage = (PlanarImage) sourceImage;

        //获取左上右下，包含仿射影子的左上角
        Envelope env = coverage.getEnvelope();

        //创建输出tif
        String outputPath = "C:\\Users\\robu\\Desktop\\坐标转换\\testTiff.tif";
        float[][] slopeData = new float[1000][1000];
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++)
                slopeData[i][j] = i + j;
        }
        GridCoverageFactory factory = new GridCoverageFactory();
        GridCoverage2D outputCoverage = factory.create("test", slopeData, env);
        GeoTiffWriter writer = new GeoTiffWriter(new File(outputPath));
        writer.write(outputCoverage, null);
        writer.dispose();


//
//        int ixtiles = sourceImage.getNumXTiles();
//        Raster raster = sourceImage.getTile(0, 0);
//
//        int itilerasterwidth = raster.getWidth();
//        int itilerasterheight = raster.getHeight();
//        int irasternumbands = raster.getNumBands();
//
//        //获取坐标系
//        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
//
//        //获取图斑名称
//        String[] names = tifReader.getGridCoverageNames();
//
//        //获取影像长宽
//        int iwidth = coverage.getRenderedImage().getWidth();
//        int iheight = coverage.getRenderedImage().getHeight();
//
//        //获取仿射因子其他参数
//        int a = coverage.getGridGeometry().gridDimensionX;
//        int b = coverage.getGridGeometry().gridDimensionY;
//        int c = coverage.getGridGeometry().axisDimensionX;
//        int d = coverage.getGridGeometry().axisDimensionY;
//
//        //获取栅格图斑个数
//        int ibandcount = coverage.getNumSampleDimensions();
//        String[] sampleDimensionNames = new String[ibandcount];
//        for (int i = 0; i < ibandcount; i++) {
//            GridSampleDimension dim = coverage.getSampleDimension(i);
//            sampleDimensionNames[i] = dim.getDescription().toString();
//        }
//
//        //获取行列对应的像元值
//        Raster sourceRaster = sourceImage.getData();
//        float[] adsaf = {0};
//        sourceRaster.getPixel(1500, 800, adsaf);
//        float ibandvalue = sourceRaster.getSampleFloat(0, 0, 0);
//
//        //获取源数据类型
//        int iDataType = coverage.getRenderedImage().getSampleModel().getDataType();

        //??栅格转矢量
        // PolygonExtractionProcess process = new PolygonExtractionProcess();
        // SimpleFeatureCollection features = process.execute(tiffCoverage, 0, Boolean.TRUE, null, null, null, null);

//        List list = new ArrayList();
//        for (int i = 0; i < strPoints.size(); i++) {
//            String strLonlat = strPoints.get(i);
//            String[] strLonlats = strLonlat.split(" ");
//
//            double lon = Double.parseDouble(strLonlats[0]),
//                    lat = Double.parseDouble(strLonlats[1]);
//
//            //构建地理坐标
//            DirectPosition position = new DirectPosition2D(crs, lon, lat);
//            float[] results = (float[]) coverage.evaluate(position);
//
//            //通过地理坐标获取行列号
//            Point2D point2d = coverage.getGridGeometry().worldToGrid(position);
//
//            //通过行列号获取地理坐标
//            GridCoordinates2D coord = new GridCoordinates2D(0, 0);
//            DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
//            float[] sss = (float[]) coverage.evaluate(tmpPos);
//
//            Map map = new HashMap();
//            map.put("lon", lon);
//            map.put("lat", lon);
//            map.put("dem", results[0]);
//            list.add(JSONObject.toJSONString(map));
//        }


    }

}
