<template>
  <div id="app">
    <pianoKeyboard v-bind:pressed_keys="pressed_keys" :pedal_pressure="pedal_pressure"/>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import pianoKeyboard from "./components/PianoKeyboard";
export default {
  name: "App",
  components: {
    pianoKeyboard
  },
  mounted: function() {
    this.connect();
  },
  created: function() {},
  data: function() {
    return {
      pressed_keys: [],
      pedal_pressure: 0
    };
  },
  methods: {
    process_message(message) {
      console.log(`Incoming Message: ${JSON.stringify(message, null, 2)}`);
      let isObject = false;
      let obj;
      try {
        obj = JSON.parse(message);
        isObject = true;
      } catch (err) {
        console.error("Cannot process as object: " + message);
      }
      console.log(`Before: ${JSON.stringify(this.pressed_keys, null, 2)}`);
      if (message != undefined && isObject) {
        if (obj.interaction_type == "key") {
          let existing_key = this.pressed_keys.filter(
            pressed_key => pressed_key.note_value == obj.note_value
          );
          if (existing_key == "") {
            this.pressed_keys.push(obj);
          } else {
            // const index = this.pressed_keys.map(e => e.note_value).indexOf(obj.note_value);
            this.pressed_keys = this.pressed_keys.filter(
              item => item.note_value !== obj.note_value
            );
            console.log();
          }
          // console.log(existing_key == '' ? 'Null' : 'Not ull');
        } else if (obj.interaction_type == "pedal") {
          if(obj.pressure != this.pedal_pressure){
            console.log(this.pedal_pressure)
            this.pedal_pressure = obj.pressure
          }
        }
      }
      console.log(`After: ${JSON.stringify(this.pressed_keys, null, 2)}`);
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/piano-info");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/real-time-piano", tick => {
            // console.log(tick);
            this.process_message(tick.body);
            // this.received_messages.push(JSON.parse(tick.body).content);
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /*color: #fc6203;*/
  margin-top: 60px;
}
html,
body {
  max-width: 100%;
  overflow-x: hidden;
  overflow-y: scroll;
  overflow-x: hidden;
}
</style>
