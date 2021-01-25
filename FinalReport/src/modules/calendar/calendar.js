function generate_year_range(start, end) {
    var years = "";
    for (var year = start; year <= end; year++) {
        years += "<option value='" + year + "'>" + year + "</option>";
    }
    return years;
}
  
var today = new Date();
var currentMonth = today.getMonth();
var currentYear = today.getFullYear();
// var selectYear = document.getElementById("year");
// var selectMonth = document.getElementById("month");

var createYear = generate_year_range(1970, 2200);

// document.getElementById("year").innerHTML = createYear;

var calendar = document.getElementById("calendar");
// var lang = calendar.getAttribute('data-lang');

var months = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];
var days = ["日", "月", "火", "水", "木", "金", "土"];

var dayHeader = "<tr>";
for (day in days) {
    dayHeader += "<th data-days='" + days[day] + "'>" + days[day] + "</th>";
}
dayHeader += "</tr>";

// 曜日ヘッダー
document.getElementById("thead_month").innerHTML = dayHeader;

monthAndYear = document.getElementById("month_and_year");
showCalendar(currentMonth, currentYear);

function next() {
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

function previous() {
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year) {
    var firstDay = ( new Date( year, month ) ).getDay();

    tbl = document.getElementById("calendar_body");
    tbl.innerHTML = "";

    monthAndYear.innerHTML = months[month] + " " + year;
    monthAndYear.innerHTML = '<p class="hmonth"><span class="hmonth">' + months[month] + '</span> </p> <p class="hyear"><span class="hyear">' + year + '</span></p>';

    // creating all cells
    var date = 1;
    for ( var i = 0; i < 6; i++ ) {
        var row = document.createElement("tr");

        for ( var j = 0; j < 7; j++ ) {
            if ( i === 0 && j < firstDay ) {
                cell = document.createElement( "td" );
                cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
            } else if (date > daysInMonth(month, year)) {
                break;
            } else {
                cell = document.createElement("td");
                cell.setAttribute("data_date", date);
                cell.setAttribute("data_month", month + 1);
                cell.setAttribute("data_year", year);
                cell.setAttribute("data_month_name", months[month]);
                cell.className = "date_picker";
                cell.innerHTML = "<span>" + date + "</span>";

                if ( date === today.getDate() && year === today.getFullYear() && month === today.getMonth() ) {
                    cell.className = "date_picker selected";
                }
                row.appendChild(cell);
                date++;
            }
        }
        tbl.appendChild(row);
    }
}

function daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
}