var extent=null,mapOptions=null,map=null,selectControl,layercode=0;//0为天地图,1为规划院
$(document).ready(function(){
	
	if(layercode==0){
		extent = new OpenLayers.Bounds(-180.0, -90.0, 180.0, 90.0);
		mapOptions = {
		        maxExtent: extent
		    };
		layer = new OpenLayers.Layer.TiandituLayer("vec_c", "http://t2.tianditu.com/DataServer",{
	        mapType:"vec_c",
	        topLevel: 3,
	        bottomLevel: 20
	    });
	}else{
		extent=new OpenLayers.Bounds(118.122911693886,28.4744311022834,120.935411693886, 31.2869311022836);
		mapOptions = {
	        maxExtent: extent
	        ,"restrictedExtent":extent
	    };
		layer=new OpenLayers.Layer.XYZ("ghylayer"
		    	,"http://21.15.121.121/cbc4cf50f18e3c4884ce563c3feef4c095cf7772/Tile/ArcGISREST/HZSYVECTOR.gis/tile/${z}/${y}/${x}");
	}

    map = new OpenLayers.Map("map",mapOptions);

    map.addLayer(layer);
    

    //map.addControls([new OpenLayers.Control.MousePosition()]);

    var style = new OpenLayers.Style({
			externalGraphic:siteUrl+"/img/map/index/${index}.png"
			,graphicWidth:31
			,graphicHeight:37
			,graphicTitle:"${label}"
			,fontColor:"#fff"
            ,cursor:"pointer"
			,graphicZIndex:0
        }, {
            context: {
                label: function(feature) {
                    return feature.attributes["title"];
                }
				,index: function(feature) {
                    return feature.attributes["index"];
                }
            }
        });
    

    var clusterStrategy=new OpenLayers.Strategy.Cluster({
            	distance:10
				,threhold:10
            });
    var styleMap= new OpenLayers.StyleMap({
            "default": style,
            "select": new OpenLayers.Style({
				externalGraphic:siteUrl+"/img/map/index/${index}v.png"
             	,graphicZIndex:111
            })
        })   
   var featureLayer=new OpenLayers.Layer.Vector("resultLayer",{
		strategies: [
            clusterStrategy
        ]
		,styleMap: styleMap
		,renderOptions:{zIndexing:true}
    });
    	
 	var select = selectControl = new OpenLayers.Control.SelectFeature(featureLayer, {
						hover: true
					});
    map.addControl(select);
    select.activate();
    map.addLayer(featureLayer);
    
    map.zoomToExtent(new OpenLayers.Bounds(118.122911693886,28.4744311022834,120.935411693886, 31.2869311022836));
    //map.setCenter(new OpenLayers.LonLat(0,0), 3);
	
}).on("queryend",function(e,response){
	var features=[];
	var list=response.result;
	var alias=response.alias;
	var dom=response.dom;
	for(var i =0;i<list.length;i++){
		var item=list[i];
		var feature=new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(item.longitude,item.latitude),{
			index:i+1
			,title:item[alias]
		});
		features.push(feature);
	}
	var featureLayer=map.getLayersByName("resultLayer")[0];
	featureLayer.removeAllFeatures();
	map.removeLayer(featureLayer);
	featureLayer.addFeatures(features);
	map.addLayer(featureLayer);
	var dataExtent=featureLayer.getDataExtent();
	if(dataExtent){
		if(features.length==1){//只有一条记录时
			var center=dataExtent.getCenterLonLat();
			map.setCenter(center,8);
		}else{
			map.zoomToExtent(dataExtent);
		}
	}else{
		map.zoomToExtent(new OpenLayers.Bounds(118.122911693886,28.4744311022834,120.935411693886, 31.2869311022836));
	}
	
	$(dom).on("mouseenter",function(){
		var index=$(this).index();
		var feature=features[index];
		if(!feature){return}
		selectControl.select(feature);
		var zoom=map.getZoom();
		map.setCenter(new OpenLayers.LonLat(feature.geometry.x,feature.geometry.y),8);
	}).on("mouseleave",function(){
		var index=$(this).index();
		var feature=features[index];
		if(!feature){return}
		selectControl.unselect(feature);
	});
	
	selectControl.onSelect=function(feature){
		var _index=feature.data.index;
		$(dom).eq(_index-1).addClass("active");
	}
	selectControl.onUnselect=function(feature){
		var _index=feature.data.index;
		$(dom).eq(_index-1).removeClass("active");
	}
})
