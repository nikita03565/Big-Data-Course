from requests import Session
from lxml.html import fromstring
import os

def get_title(page):
    return page.xpath("//td[@class='text-center']//b")[0].text.strip()

def parse_and_save_page(page_url, file_name_prefix=''):
    print("requesting", page_url)
    r = session.get(page_url)

    tree = fromstring(r.text)
    title = get_title(tree)
    f_name = os.path.join(data_dir, f"{file_name_prefix}{title}.html")
    with open(f_name, "w") as f:
        f.write(r.text)
        print("saved", f_name)
    return tree


data_dir = "data"
headers = {
    "user-agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36"
}
session = Session()
session.headers.update(headers)

base_url = "http://notelections.online"
url = "http://notelections.online/region/region/izbirkom?action=show&root=1&tvd=100100084849066&vrn=100100084849062&region=0&global=1&sub_region=0&prver=0&pronetvd=null&vibid=100100084849066&type=227"

tree = parse_and_save_page(url)

row_names = []
table = tree.xpath("//table[@id='fix-columns-table']")[0]

for row in table.xpath(".//tr")[1:]:
    row_names.append(row.xpath(".//td")[1].text.strip())

district_link_elements = table.xpath(".//a")
district_names = [d.text.strip() for d in district_link_elements]
district_hrefs = [d.get("href") for d in district_link_elements]

for href in district_hrefs:
    d_url = f"{base_url}{href}"
    tree = parse_and_save_page(d_url, 'overview_')
    detailed_table = tree.xpath("//table[@id='fix-columns-table']")[0]

    detailed_link_elements = detailed_table.xpath(".//a")
    detailed_names = [d.text.strip() for d in detailed_link_elements]
    detailed_hrefs = [d.get("href") for d in detailed_link_elements]
    for d_href in detailed_hrefs:
        detailed_url = f"{base_url}{d_href}"
        tree = parse_and_save_page(detailed_url, 'detailed_')
# print(district_names)
# print(len(districts))
# print(row_names)


