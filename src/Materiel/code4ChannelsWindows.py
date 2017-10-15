import pyaudio
import wave,struct
import matplotlib.pyplot as plt
import scipy.io.wavfile as wavefile

CHUNK = 1024 
FORMAT = pyaudio.paInt16 #paInt8
CHANNELS = 2
RATE = 44100 #sample rate
RECORD_SECONDS = 2
WAVE_OUTPUT_FILENAME = "output.wav"

while(True):
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
    f1 = open("../../data/audio1.txt","w")
    f2 = open("../../data/audio2.txt","w")
    f3 = open("../../data/audio3.txt","w")
    f4 = open("../../data/audio4.txt","w")
    for i in range(len(data)):
        f1.write(str(data[i][0])+"\n")
        f2.write(str(data[i][0])+"\n")
        f3.write(str(data[i][1])+"\n")
        f4.write(str(data[i][1])+"\n")
    f1.close()
    f2.close()
    f3.close()
    f4.close()
    
