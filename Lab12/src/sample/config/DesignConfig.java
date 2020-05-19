package sample.config;

import javafx.scene.layout.Pane;
import javafx.scene.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesignConfig implements Serializable {
    private List<NodeConfig> nodes = new ArrayList<>();


    public DesignConfig(){}

    public DesignConfig(Pane pane){
        for(Node child: pane.getChildren()){
            nodes.add(new NodeConfig(child));
        }
    }

    public List<NodeConfig> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeConfig> nodes) {
        this.nodes = nodes;
    }

    public Pane create(){
        Pane pane = new Pane();
        var children = pane.getChildren();

        for(var node: nodes){
            children.add(node.create().getNode());
        }

        return pane;
    }
}
