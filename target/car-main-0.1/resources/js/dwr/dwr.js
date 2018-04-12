if (typeof dwr == 'undefined' || dwr.engine == undefined) throw new Error('You must include DWR engine before including this file');

(function() {
	if (dwr.engine._getObject("dwr") == undefined) {
		var p;

		p = {};

		/**
		 * @param {function|Object} callback callback function or options object
		 */
		p.eleSignCallback = function(callback) {
			return dwr.engine._execute(p._path, 'dwr', 'eleSignCallback', arguments);
		};

		/**
		 * @param {function|Object} callback callback function or options object
		 */
		p.lcbEleSignCallback = function(callback) {
			return dwr.engine._execute(p._path, 'dwr', 'lcbEleSignCallback', arguments);
		};

		dwr.engine._setObject("dwr", p);
	}
})();