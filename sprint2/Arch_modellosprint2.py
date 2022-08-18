from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('modellosprint2Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxled', graph_attr=nodeattr):
          led=Custom('led(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxsonar', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          pathexec=Custom('pathexec(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwastetruck', graph_attr=nodeattr):
          waste_truck_mock=Custom('waste_truck_mock','./qakicons/symActorSmall.png')
     with Cluster('ctxanalisiproblema', graph_attr=nodeattr):
          trolley=Custom('trolley','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
     trolley >> Edge(color='magenta', style='solid', xlabel='dopath') >> pathexec
     trolley >> Edge( xlabel='alarm', **eventedgeattr) >> sys
     wasteservice >> Edge(color='blue', style='solid', xlabel='cmd') >> led
     wasteservice >> Edge(color='blue', style='solid', xlabel='resume') >> trolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='go_indoor') >> trolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickup') >> trolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='trasf') >> trolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='deposit') >> trolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='ritorno_home') >> trolley
     wasteservice >> Edge(color='blue', style='solid', xlabel='stop') >> trolley
     sonar >> Edge(color='blue', style='solid', xlabel='stop') >> wasteservice
     sonar >> Edge(color='blue', style='solid', xlabel='resume') >> wasteservice
     waste_truck_mock >> Edge(color='magenta', style='solid', xlabel='load_req') >> wasteservice
diag
