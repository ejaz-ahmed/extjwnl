package net.didion.jwnl.data.relationship;

import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;

/**
 * An asymmetric relationship is one whose source and target synsets have lineages with a definite divergence point.
 * The commonParentIndex is the index of the node in the relationship that represents this divergence point.
 * <p/>
 * For example, in finding a hypernym  relationship between dog and cat, the relationship is dog -> canine ->
 * carnivore -> feline -> cat. The ancestry of "dog" and the ancestry of "cat" diverge at "carnivore," so
 * the common parent index is thus 2.
 */
public class AsymmetricRelationship extends Relationship {
    /**
     * The index of the node in the relationship that represents the point
     * at which the source and target nodes' ancestries diverge.
     */
    private int commonParentIndex;
    private transient int cachedRelativeTargetDepth = -1;

    public AsymmetricRelationship(
            PointerType type, PointerTargetNodeList nodes, int commonParentIndex, Synset sourceSynset, Synset targetSynset) {

        super(type, nodes, sourceSynset, targetSynset);
        this.commonParentIndex = commonParentIndex;
    }

    public int getCommonParentIndex() {
        return commonParentIndex;
    }

    /**
     * Get the depth of the target, from the commonParentIndex, relative to the depth of the source.
     * If both target and source are equidistant from the commonParentIndex, this method returns 0;
     */
    public int getRelativeTargetDepth() {
        if (cachedRelativeTargetDepth == -1) {
            int distSourceToParent = commonParentIndex;
            int distParentToTarget = (getNodeList().size() - 1) - commonParentIndex;
            cachedRelativeTargetDepth = distParentToTarget - distSourceToParent;
        }
        return cachedRelativeTargetDepth;
    }

    public Relationship reverse() throws CloneNotSupportedException {
        PointerTargetNodeList list = ((PointerTargetNodeList) getNodeList().deepClone()).reverse();
        int commonParentIndex = (list.size() - 1) - getCommonParentIndex();
        for (int i = 0; i < list.size(); i++) {
            if (i != commonParentIndex) {
                ((PointerTargetNode) list.get(i)).setType(getType().getSymmetricType());
            }
        }
        return new AsymmetricRelationship(getType(), list, commonParentIndex, getSourceSynset(), getTargetSynset());
    }
}