import pyaudio
import wave,struct
import matplotlib.pyplot as plt
import scipy.io.wavfile as wavefile

CHUNK = 1024 
FORMAT = pyaudio.paInt16 #paInt8
CHANNELS = 4
RATE = 44100 #sample rate
RECORD_SECONDS = 5
WAVE_OUTPUT_FILENAME = "output.wav"

p = pyaudio.PyAudio()

stream = p.open(format=FORMAT,
                channels=CHANNELS,
                rate=RATE,
                input=True,
                frames_per_buffer=CHUNK) #buffer

print("* recording")

frames = []

for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
    data = stream.read(CHUNK)
    frames.append(data) # 2 bytes(16 bits) per channel

print("* done recording")

stream.stop_stream()
stream.close()
p.terminate()

wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
wf.setnchannels(CHANNELS)
wf.setsampwidth(p.get_sample_size(FORMAT))
wf.setframerate(RATE)
wf.writeframes(b''.join(frames))
wf.close()

rate,data=wavefile.read(WAVE_OUTPUT_FILENAME)
f1 = open("audio1.txt","w")
f2 = open("audio2.txt","w")
f3 = open("audio3.txt","w")
f4 = open("audio4.txt","w")
for i in range(len(data)):
    f1.write(str(data[i][0])+"\r\n")
    f2.write(str(data[i][1])+"\r\n")
    f3.write(str(data[i][2])+"\r\n")
    f4.write(str(data[i][3])+"\r\n")
f1.close()
f2.close()
f3.close()
f4.close()


