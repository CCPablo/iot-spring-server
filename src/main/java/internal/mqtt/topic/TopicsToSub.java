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
        String[] topicToCheckMembers = topicToCheck.split("/");
        boolean notSameMembersLength = topicMembers.length != topicToCheckMembers.length;
        if (notSameMembersLength) {
            return false;
        }
        for (int i = 0; i < topicMembers.length; i++) {
            if (!topicMemberMatch(topicMembers[i], topicToCheckMembers[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean topicMemberMatch(String topic, String topicToCheck) {
        if (!topic.equals("+") && !topic.equals("#")) {
            return topic.equals(topicToCheck);
        }
        return true;
    }

    public int getQoS() {
        return qoS;
    }

}
