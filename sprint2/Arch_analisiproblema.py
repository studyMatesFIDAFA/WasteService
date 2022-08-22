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
with Diagram('analisiproblemaArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxraspy', graph_attr=nodeattr):
          led=Custom('led(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          pathexec=Custom('pathexec(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxanalisiproblema', graph_attr=nodeattr):
          distancefilter=Custom('distancefilter','./qakicons/symActorSmall.png')
          trolley=Custom('trolley','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
     sys >> Edge(color='red', style='dashed', xlabel='sonardata') >> distancefilter
     distancefilter >> Edge(color='blue', style='solid', xlabel='stop') >> wasteservice
     distancefilter >> Edge(color='blue', style='solid', xlabel='resume') >> wasteservice
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
diag
