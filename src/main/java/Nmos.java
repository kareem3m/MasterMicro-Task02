import com.google.gson.annotations.SerializedName;

public class Nmos extends Component {
    @SerializedName(value = "m(l)")
    private Value m_l;
    private final Netlist netlist;

    Nmos(String id, String type, Value m_l, Netlist netlist) {
        super(id, type);
        this.m_l = m_l;
        this.netlist = netlist;
    }

    public Value getM_l() {
        return m_l;
    }

    public String[] getNetlist() {
        return new String[]{netlist.drain, netlist.gate, netlist.source};
    }

    public static class Netlist {
        private final String drain, gate, source;

        public Netlist(String drain, String gate, String source) {
            this.drain = drain;
            this.gate = gate;
            this.source = source;
        }

        public String getDrain() {
            return drain;
        }

        public String getGate() {
            return gate;
        }

        public String getSource() {
            return source;
        }
    }
}
