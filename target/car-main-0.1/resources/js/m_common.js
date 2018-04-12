$(function() {
	$(window).resize(function() {
		$('table').each(function() {
			if ($.data(this, 'datagrid')) {
				var datagrid = $(this);
				setTimeout(function() {
					datagrid.datagrid('resize');
				}, 200);
			}
		});
	});
});
/**
 * ie8location不支持base，通过location进行页面跳转
 * 请使用gotoPage
 */
function gotoPage(path) {
	var base = document.getElementsByTagName('base')[0].getAttribute('href');
	location.href = base + path;
}


