package internal.mqtt.topic;

public enum TopicsToSub {

    UNIT_VALUE("/node/unit/value", 0),
    NODE_CONNECT_INIT("/node/init", 2);

    private final String topic;
    private final int qoS;

    TopicsToSub(final String topic, int qoS) {
        this.topic = topic;
        this.qoS = qoS;
    }

    public String getTopic() {
        return topic;
    }

    public boolean topicMatch(String topicToCheck) {
        String[] topicMembers = topic.split("/");
        String[] toCheckTopicMembers = topicToCheck.split("/");
        if (topicMembers.length != toCheckTopicMembers.length) {
            return false;
        }
        for (int i = 0; i < topicMembers.length; i++) {
            if (!topicMembers[i].equals("+") && !topicMembers[i].equals("#")) {
                if (!topicMembers[i].equals(toCheckTopicMembers[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getQoS() {
        return qoS;
    }

}
