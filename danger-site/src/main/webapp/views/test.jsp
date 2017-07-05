<!doctype html>
<html>
	<head>
		<meta charset="utf-8"/>
		<style>
			.map{float:left;border:1px solid #ddd;position:relative;overflow:hidden;}
		</style>
	</head>
	<body>
		<div class="map" id="map1" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map2" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map3" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map4" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map5" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map6" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map7" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map8" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map9" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map10" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map11" data-feature-type="point" style="width:300px;height:200px;"></div>
		<div class="map" id="map12" data-feature-type="line" style="width:300px;height:200px;"></div>
	
		<script src="../lib/arcgis3.9/init.js"></script>
		
		<script>
		require(["esri/map"
		         ,"esri/layers/FeatureLayer"
		         ,"esri/symbols/PictureMarkerSymbol"
		         ,"esri/symbols/SimpleLineSymbol"
		         ,"esri/symbols/SimpleFillSymbol"
		         ,"esri/graphic"
		         ,"esri/layers/GraphicsLayer"
		         ,"esri/geometry/Point"
		         ,"esri/geometry/Polyline"
		         ,"esri/geometry/Polygon"
		         , "dojo/domReady!"], function(Map
		        		 ,FeatureLayer
		        		 ,PictureMarkerSymbol
		        		 ,SimpleLineSymbol
		        		 ,SimpleFillSymbol
		        		 ,Graphic
		        		 ,GraphicsLayer
		        		 ,Point
		        		 ,Polyline
		        		 ,Polygon) { 
			//初始化地图控件
			for(var i =1;i<=12;i++){
				(function(i){
					var map = new Map("map"+i, {
					    center: [114, 34.5],
					    zoom: 8,
					    basemap: "streets"
					  });
					removeMapAction(map);
					var type="polygon";
					addMarker(map,type);
				})(i);
			}
			
		  	function removeMapAction(map){
		  		//移除地图放大缩小
		  		map.on("load", function(event) {
					var _map=event.map;
					_map.disablePan();
					_map.disableScrollWheelZoom();
					_map.disableRubberBandZoom();
					_map.disableClickRecenter();
					_map.disableDoubleClickZoom();
					_map.disableShiftDoubleClickZoom();
					_map.disableKeyboardNavigation();
					_map.hidePanArrows();
					_map.hideZoomSlider();
					_map.disableMapNavigation();
				  });
		  	}
		  	
		  	function addMarker(map,type){
		  		var geometry,symbol=null;
		  		switch(type){
		  		default:
		  		case "point":
		  			geometry = new Point(114, 34.5);
			  	    symbol = new PictureMarkerSymbol("http://localhost:8081/img/close.png", 20, 20);
			  	    
		  			break;
		  		case "line":
		  			var pj={paths:[[[114, 34.5], [114.58,35.55],
		  			              [114.57,35.58],[114,35.6]]]}
		  			geometry = new Polyline(pj);
		  			symbol=new SimpleLineSymbol();
		  			break;
		  		case "polygon":
		  			var pj={rings:[[[114, 34.5], [114.58,35.55],
			  			              [114.57,35.58],[114,35.6]]]}
		  			geometry = new Polygon(pj);
		  			symbol=new SimpleFillSymbol();
		  			break;
		  		}
		  		var picGraphic = new Graphic(geometry, symbol);
		  	  	var gLayer=new GraphicsLayer();
		  	  	gLayer.add(picGraphic);
		  	  	map.addLayer(gLayer);
		  	}
		  	
		});
		</script>
	</body>
</html>