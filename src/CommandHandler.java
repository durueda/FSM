import java.io.*;
import java.util.*;

public class CommandHandler {
        private FSM fsm;
        private Logger logger;

        public CommandHandler(FSM fsm, Logger logger) {
            this.fsm = fsm;
            this.logger = logger;
        }
}

