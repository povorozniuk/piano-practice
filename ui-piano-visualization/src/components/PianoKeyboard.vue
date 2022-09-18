<template>

  <div class="container">
    <div class="piano-keyboard">
      <div data-note="A0" class="key white" v-bind:class="{ active: isPressed('A0') }"></div>
      <div data-note="A0#" class="key black" v-bind:class="{ active: isPressed('A0#') }"></div>
      <div data-note="B0" class="key white" v-bind:class="{ active: isPressed('B0') }"></div>
      <div data-note="C1" class="key white" v-bind:class="{ active: isPressed('C1') }"></div>
      <div data-note="C1#" class="key black" v-bind:class="{ active: isPressed('C1#') }"></div>
      <div data-note="D1" class="key white" v-bind:class="{ active: isPressed('D1') }"></div>
      <div data-note="D1#" class="key black" v-bind:class="{ active: isPressed('D1#') }"></div>
      <div data-note="E1" class="key white" v-bind:class="{ active: isPressed('E1') }"></div>
      <div data-note="F1" class="key white" v-bind:class="{ active: isPressed('F1') }"></div>
      <div data-note="F1#" class="key black" v-bind:class="{ active: isPressed('F1#') }"></div>
      <div data-note="G1" class="key white" v-bind:class="{ active: isPressed('G1') }"></div>
      <div data-note="G1#" class="key black" v-bind:class="{ active: isPressed('G1#') }"></div>
      <div data-note="A1" class="key white" v-bind:class="{ active: isPressed('A1') }"></div>
      <div data-note="A1#" class="key black" v-bind:class="{ active: isPressed('A1#') }"></div>
      <div data-note="B1" class="key white" v-bind:class="{ active: isPressed('B1') }"></div>

      <div data-note="C2" class="key white" v-bind:class="{ active: isPressed('C2') }"></div>
      <div data-note="C2#" class="key black" v-bind:class="{ active: isPressed('C2#') }"></div>
      <div data-note="D2" class="key white" v-bind:class="{ active: isPressed('D2') }"></div>
      <div data-note="D2#" class="key black" v-bind:class="{ active: isPressed('D2#') }"></div>
      <div data-note="E2" class="key white" v-bind:class="{ active: isPressed('E2') }"></div>
      <div data-note="F2" class="key white" v-bind:class="{ active: isPressed('F2') }"></div>
      <div data-note="F2#" class="key black" v-bind:class="{ active: isPressed('F2#') }"></div>
      <div data-note="G2" class="key white" v-bind:class="{ active: isPressed('G2') }"></div>
      <div data-note="G2#" class="key black" v-bind:class="{ active: isPressed('G2#') }"></div>
      <div data-note="A2" class="key white" v-bind:class="{ active: isPressed('A2') }"></div>
      <div data-note="A2#" class="key black" v-bind:class="{ active: isPressed('A2#') }"></div>
      <div data-note="B2" class="key white" v-bind:class="{ active: isPressed('B2') }"></div>

      <div data-note="C3" class="key white" v-bind:class="{ active: isPressed('C3') }"></div>
      <div data-note="C3#" class="key black" v-bind:class="{ active: isPressed('C3#') }"></div>
      <div data-note="D3" class="key white" v-bind:class="{ active: isPressed('D3') }"></div>
      <div data-note="D3#" class="key black" v-bind:class="{ active: isPressed('D3#') }"></div>
      <div data-note="E3" class="key white" v-bind:class="{ active: isPressed('E3') }"></div>
      <div data-note="F3" class="key white" v-bind:class="{ active: isPressed('F3') }"></div>
      <div data-note="F3#" class="key black" v-bind:class="{ active: isPressed('F3#') }"></div>
      <div data-note="G3" class="key white" v-bind:class="{ active: isPressed('G3') }"></div>
      <div data-note="G3#" class="key black" v-bind:class="{ active: isPressed('G3#') }"></div>
      <div data-note="A3" class="key white" v-bind:class="{ active: isPressed('A3') }"></div>
      <div data-note="A3#" class="key black" v-bind:class="{ active: isPressed('A3#') }"></div>
      <div data-note="B3" class="key white" v-bind:class="{ active: isPressed('B3') }"></div>

      <div data-note="C4" class="key white" v-bind:class="{ active: isPressed('C4') }"></div>
      <div data-note="C4#" class="key black" v-bind:class="{ active: isPressed('C4#') }"></div>
      <div data-note="D4" class="key white" v-bind:class="{ active: isPressed('D4') }"></div>
      <div data-note="D4#" class="key black" v-bind:class="{ active: isPressed('D4#') }"></div>
      <div data-note="E4" class="key white" v-bind:class="{ active: isPressed('E4') }"></div>
      <div data-note="F4" class="key white" v-bind:class="{ active: isPressed('F4') }"></div>
      <div data-note="F4#" class="key black" v-bind:class="{ active: isPressed('F4#') }"></div>
      <div data-note="G4" class="key white" v-bind:class="{ active: isPressed('G4') }"></div>
      <div data-note="G4#" class="key black" v-bind:class="{ active: isPressed('G4#') }"></div>
      <div data-note="A4" class="key white" v-bind:class="{ active: isPressed('A4') }"></div>
      <div data-note="A4#" class="key black" v-bind:class="{ active: isPressed('A4#') }"></div>
      <div data-note="B4" class="key white" v-bind:class="{ active: isPressed('B4') }"></div>

      <div data-note="C5" class="key white" v-bind:class="{ active: isPressed('C5') }"></div>
      <div data-note="C5#" class="key black" v-bind:class="{ active: isPressed('C5#') }"></div>
      <div data-note="D5" class="key white" v-bind:class="{ active: isPressed('D5') }"></div>
      <div data-note="D5#" class="key black" v-bind:class="{ active: isPressed('D5#') }"></div>
      <div data-note="E5" class="key white" v-bind:class="{ active: isPressed('E5') }"></div>
      <div data-note="F5" class="key white" v-bind:class="{ active: isPressed('F5') }"></div>
      <div data-note="F5#" class="key black" v-bind:class="{ active: isPressed('F5#') }"></div>
      <div data-note="G5" class="key white" v-bind:class="{ active: isPressed('G5') }"></div>
      <div data-note="G5#" class="key black" v-bind:class="{ active: isPressed('G5#') }"></div>
      <div data-note="A5" class="key white" v-bind:class="{ active: isPressed('A5') }"></div>
      <div data-note="A5#" class="key black" v-bind:class="{ active: isPressed('A5#') }"></div>
      <div data-note="B5" class="key white" v-bind:class="{ active: isPressed('B5') }"></div>

      <div data-note="C6" class="key white" v-bind:class="{ active: isPressed('C6') }"></div>
      <div data-note="C6#" class="key black" v-bind:class="{ active: isPressed('C6#') }"></div>
      <div data-note="D6" class="key white" v-bind:class="{ active: isPressed('D6') }"></div>
      <div data-note="D6#" class="key black" v-bind:class="{ active: isPressed('D6#') }"></div>
      <div data-note="E6" class="key white" v-bind:class="{ active: isPressed('E6') }"></div>
      <div data-note="F6" class="key white" v-bind:class="{ active: isPressed('F6') }"></div>
      <div data-note="F6#" class="key black" v-bind:class="{ active: isPressed('F6#') }"></div>
      <div data-note="G6" class="key white" v-bind:class="{ active: isPressed('G6') }"></div>
      <div data-note="G6#" class="key black" v-bind:class="{ active: isPressed('G6#') }"></div>
      <div data-note="A6" class="key white" v-bind:class="{ active: isPressed('A6') }"></div>
      <div data-note="A6#" class="key black" v-bind:class="{ active: isPressed('A6#') }"></div>
      <div data-note="B6" class="key white" v-bind:class="{ active: isPressed('B6') }"></div>

      <div data-note="C7" class="key white" v-bind:class="{ active: isPressed('C7') }"></div>
      <div data-note="C7#" class="key black" v-bind:class="{ active: isPressed('C7#') }"></div>
      <div data-note="D7" class="key white" v-bind:class="{ active: isPressed('D7') }"></div>
      <div data-note="D7#" class="key black" v-bind:class="{ active: isPressed('D7#') }"></div>
      <div data-note="E7" class="key white" v-bind:class="{ active: isPressed('E7') }"></div>
      <div data-note="F7" class="key white" v-bind:class="{ active: isPressed('F7') }"></div>
      <div data-note="F7#" class="key black" v-bind:class="{ active: isPressed('F7#') }"></div>
      <div data-note="G7" class="key white" v-bind:class="{ active: isPressed('G7') }"></div>
      <div data-note="G7#" class="key black" v-bind:class="{ active: isPressed('G7#') }"></div>
      <div data-note="A7" class="key white" v-bind:class="{ active: isPressed('A7') }"></div>
      <div data-note="A7#" class="key black" v-bind:class="{ active: isPressed('A7#') }"></div>
      <div data-note="B7" class="key white" v-bind:class="{ active: isPressed('B7') }"></div>

      <div data-note="C8" class="key white" v-bind:class="{ active: isPressed('C8') }"></div>
    </div>
    <!-- {{this.pedal_pressure}} -->

    <svg width="1000" height="400">
      <defs>
        <linearGradient id="progress" x1="0" y1="1" x2="0" y2="0">
          <!-- <stop id="stop1" offset="0" stop-color="lightblue" /> -->
          <stop id="stop1" offset="0" stop-color="lightgrey" />
          <stop id="stop2" offset="0" stop-color="white" />
        </linearGradient>
      </defs>
      <svg width="1000" height="110">
        <rect width="1000" height="100" fill="url(#progress)" >
        </rect>
      </svg>
      <!-- <circle id="my-shape" cx="200" cy="200" r="200" fill="url(#progress)" /> -->
    </svg>
    
  </div>
</template>

<script>
// import Vue from "vue";

export default {
  name: "pianoKeyboard",
  components: {},
  mounted() {},
  methods: {
    setProgress: function(amt) {
      {
        amt = amt < 0 ? 0 : amt > 1 ? 1 : amt;
        document.getElementById("stop1").setAttribute("offset", amt);
        document.getElementById("stop2").setAttribute("offset", amt);
      }
    },
    isPressed: function(noteValue) {
      // console.log(`Checking whether ${noteValue} is pressed`);
      // console.log(this.pressed_keys);
      if (this.pressed_keys == undefined || this.pressed_keys == null)
        return false;
      let isPressed = this.pressed_keys.filter(
        key => key.note_value == noteValue
      );

      // console.log(isPressed);
      return isPressed.length > 0;
    }
  },
  watch: {
    pedal_pressure: function(val) {
      console.log(val);
      this.setProgress(val / 100);
    }
  },
  data() {
    return {};
  },
  props: ["pressed_keys", "pedal_pressure"]
};
</script>

<style scoped>
*,
*::before,
*::after {
  box-sizing: border-box;
}
.container {
  background-color: #ffffff;
  min-height: 10vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.piano-keyboard {
  display: flex;
  overflow: hidden;
}

.key {
  height: calc(var(--width) * 4);
  width: var(--width);
}

.white {
  --width: 1.5rem;
  background-color: white;
  border: 1px solid #333;
}

.white.active {
  background-color: #fc6203;
}

.black {
  --width: 1rem;
  background-color: black;
  margin-left: calc(var(--width) / -2);
  margin-right: calc(var(--width) / -2);
  z-index: 2;
}

.black.active {
  background-color: #fc6203;
}

.pedal {
  background: #7d7d7d;
  background: -webkit-linear-gradient(right, #7d7d7d, #ffffff);
  background: -moz-linear-gradient(right, #7d7d7d, #ffffff);
  background: linear-gradient(to left, #7d7d7d, #ffffff);
}
</style>
