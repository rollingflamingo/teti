import 'datatables.net';
import '../../scss/components/_dataTables.scss';

let defOptions = {
    "info" : false,
    "searching" : false,
    "serverSide": true,
    "ordering": false,
    "paging" : false,
    "lengthChange": false
};

export function initDataTable(selector, opt) {
    let params = $.extend(defOptions, opt);
    let $selector = $(selector);
    return $selector.DataTable(params);
}