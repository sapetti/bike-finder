#!/bin/bash
echo "Bike Scraper started!"

# Delete temporal/result files
rm -f tmp_file results-orbea.csv

# Download main web page bikes
curl https://www.orbea.com/es-es/bicicletas/montana/cat > tmp_file


# We are looking for the following fields
#    Name: <h2>OIZ  M50 19</h2> 
#    Price and currency: <strong class="pvp">2.999 <span itemprop="priceCurrency" content="EUR">€</span></strong>
#    Detail url: <a class="button buy " href="/es-es/bicicletas/montana/oiz/cat/oiz-29-sp-m-ltd-19">Ver modelo</a>


# Write headers to CSV file
echo "name;price;currency;detail_url;" > results-orbea.csv

# Scrape the downloaded page to get all the bikes
#   1. Apply the filters to get Name, Price, Currency and DetailUrl fields described above using the .awk filter file
#   2. Remove tabs at the beginning of each line
#   3. Remove ending newline character for the <strong> tag
#   4. Remove ending newline character for the <h2> tag
#   5. Get the fields using regex groups
#   6. Dump the result in the .csv file
awk -f orbea_filter.awk tmp_file \
    | sed -e 's/^[ \t]*//' \
    | sed -r '/<strong/ {N; s/ *\n/ /}' \
    | sed -r '/<h2>/ {N; s/ *\n//}' \
    | sed -nr "s/<h2>([^<]+)<\/h2><strong class=\"pvp\">([^<]+)\s<span itemprop=\"priceCurrency\" content=\"([^\"]+)\">€<\/span><\/strong>\s<a class=\"[^\"]+\" href=\"([^\"]+)\">Ver modelo<\/a>/\1;\2;\3;\4;/p" \
    >> resultsorbea.csv

echo 'GET'
echo `curl -X GET http://localhost:8080/data`

echo 'POST'
curl -X POST \
    -H 'Content-Type: text/csv' \
    -d @resultsorbea.csv \
    http://localhost:8080/data

    