public class Resistor extends Component {
    private final Value resistance;
    private final Netlist netlist;

    Resistor(String id, String type, Value resistance, Netlist netlist) {
        super(id, type);
        this.resistance = resistance;
        this.netlist = netlist;
    }

    public Value getResistance() {
        return resistance;
    }

    public String[] getNetlist() {
        return new String[]{netlist.t1, netlist.t2};
    }

    public static class Netlist {
        private final String t1;
        private final String t2;

        public Netlist(String t1, String t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        public String getT1() {
            return t1;
        }

        public String getT2() {
            return t2;
        }
    }
}
