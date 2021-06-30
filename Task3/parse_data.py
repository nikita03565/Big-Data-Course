import os

import pandas as pd
from lxml.html import fromstring

data_dir = "data"
output_dir = "parsed"
files = [name for name in os.listdir(data_dir) if name.startswith("detailed")]


def parse_table(table, district, tik):
    header_elements = table.xpath(".//tr[th]")[0].xpath(".//th")
    columns = []
    for header_row in header_elements:
        text_content = header_row.text_content().strip()
        columns.append({
            "name": text_content,
            "district": district,
            "tik": tik
        })
    rows = table.xpath(".//tr[not(th)]")
    for row in rows:
        row_name = row.xpath(".//td[contains(@class, 'second-fix-col')]")[0].text.strip()
        for idx, td in enumerate(row.xpath(".//td")):
            value = td.text_content().strip().split("\n")[0]
            columns[idx][row_name] = value
    return [el for el in columns if el["name"].startswith("УИК")]


for i, f_name in enumerate(files):
    _, district, tik = f_name.replace(".html", "").split("_")
    with open(os.path.join(data_dir, f_name), "r") as f:
        tree = fromstring(f.read())
    table = tree.xpath("//table[@id='fix-columns-table']")[0]
    parsed = parse_table(table, district, tik)
    df = pd.DataFrame(parsed)
    df.set_index("name", inplace=True)
    df.to_csv(os.path.join(output_dir, "parsed.csv"), mode="a", header=i == 0)
