function expressDataParse(d, b, c, a) {
    if (a) { $("." + b).remove() }
    if (c.path.length > 0) { d.selectAll("." + b).data(c.path).enter().append("g").attr("class", function(g) {
            var e = d3.select(this);
            var f = e.append(g.type);
            g.style.forEach(function(h) { f.attr(h.attr, h.val) });
            return b });
        if ((c.cX != 0) || (c.cY != 0)) { d.attr("transform", "translate(" + c.cX + "," + c.cY + ")") } } }

function hairDataParse(e, a, f, d, c, b) {
    if (b) { $("." + d).remove() }
    if ("frontPath" in c) { e.selectAll("." + d).data(c.frontPath).enter().append("g").attr("class", function(i) {
            var g = d3.select(this);
            var h;
            if ("type" in i) { h = g.append(i.type) } else { h = g.append("path").attr("d", i.path) }
            i.style.forEach(function(j) { h.attr(j.attr, j.val) });
            return d });
        if ((c.cX[0] != 0) || (c.cY[0] != 0)) { e.attr("transform", "translate(" + c.cX[0] + "," + c.cY[0] + ")") } }
    if ("middlePath" in c) { a.selectAll("." + d).data(c.middlePath).enter().append("g").attr("class", function(i) {
            var g = d3.select(this);
            var h = g.append("path").attr("d", i.path);
            i.style.forEach(function(j) { h.attr(j.attr, j.val) });
            return d });
        if ((c.cX[2] != 0) || (c.cY[2] != 0)) { f.attr("transform", "translate(" + c.cX[1] + "," + c.cY[1] + ")") } }
    if ("backPath" in c) { f.selectAll("." + d).data(c.backPath).enter().append("g").attr("class", function(i) {
            var g = d3.select(this);
            var h = g.append("path").attr("d", i.path);
            i.style.forEach(function(j) { h.attr(j.attr, j.val) });
            return d });
        if ((c.cX[1] != 0) || (c.cY[1] != 0)) { f.attr("transform", "translate(" + c.cX[1] + "," + c.cY[1] + ")") } } }