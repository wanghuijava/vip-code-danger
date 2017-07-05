/**
 *
 * @description 实现IE8及以下的Array方法
 * @returns {Array}
 */

Array.prototype.forEach=function(fn){
    for(var i =0;i<this.length;i++){
        fn(this[i],i,this);
    }
}
