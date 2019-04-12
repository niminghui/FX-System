var prov = document.getElementById('province');
var city = document.getElementById('city');
var country = document.getElementById('country');


/*用于保存当前所选的省市区*/
var current = {
    prov: '',
    city: '',
    country: ''
};

/*自动加载省份列表*/
(function showProv() {
    var len = provice.length;
    for (var i = 0; i < len; i++) {
        var provOpt = document.createElement('option');
        provOpt.innerText = provice[i]['name'];
        provOpt.value = provice[i]['name'];
        prov.appendChild(provOpt);
    }
})();

/*根据所选的省份来显示城市列表*/
function showCity(obj) {
    var index = obj.selectedIndex - 1;
    if (index !== current.prov) {
        current.prov = index;
    }
    if (index != null) {
        city.length = 1;
        var cityLen = provice[index]['city'].length;
        for (var j = 0; j < cityLen; j++) {
            var cityOpt = document.createElement('option');
            cityOpt.innerText = provice[index]['city'][j].name;
            cityOpt.value = provice[index]['city'][j].name;
            city.appendChild(cityOpt);
        }
    }
}

/*根据所选的城市来显示县区列表*/
function showCountry(obj) {
    var index = obj.selectedIndex - 1;
    current.city = index;
    if (index != null) {
        country.length = 1; //清空之前的内容只留第一个默认选项
        var countryLen = provice[current.prov]['city'][index].districtAndCounty.length;
        if (countryLen === 0) {
            return;
        }
        for (var n = 0; n < countryLen; n++) {
            var countryOpt = document.createElement('option');
            countryOpt.innerText = provice[current.prov]['city'][index].districtAndCounty[n];
            countryOpt.value = provice[current.prov]['city'][index].districtAndCounty[n];
            country.appendChild(countryOpt);
        }
    }
}

/*选择县区之后的处理函数*/
function selectCountry(obj) {
    current.country = obj.selectedIndex - 1;
    prov.value = provice[current.prov].name;
    city.value = provice[current.prov]['city'][current.city].name;
    country.value = provice[current.prov]['city'][current.city].districtAndCounty[current.country];
}
