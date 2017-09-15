package audiocaptureplugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import mo.capture.RecordableConfiguration;
import mo.organization.Configuration;
import mo.organization.Participant;
import mo.organization.ProjectOrganization;
import static mo.organization.ProjectOrganization.logger;

public class AudioCaptureConfiguration implements RecordableConfiguration {
    
    private String id;    
    private int op_mic;
    AudioRecorder wr;  

    AudioCaptureConfiguration(String id,int op_mic) {
        this.id = id;
        this.op_mic = op_mic;
    }
    
    @Override
    public void setupRecording(File stageFolder, ProjectOrganization org, Participant p) {
         wr = new AudioRecorder(stageFolder, org, p,op_mic, this);
    }

    @Override
    public void startRecording() {
            wr.StartRecord();
    }

    @Override
    public void stopRecording() {
        wr.StopRecord();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public File toFile(File parent) {
        try {
            File f = new File(parent, "audio_"+id+"-"+op_mic+".xml");
            f.createNewFile();
            return f;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Configuration fromFile(File file) {
       String fileName = file.getName();
        if (fileName.contains("_") && fileName.contains(".") && fileName.contains("-")){
            String newId = fileName.substring(fileName.indexOf('_') + 1, fileName.lastIndexOf("-"));            
            String newOp_Mic = fileName.substring(fileName.indexOf('-') + 1, fileName.lastIndexOf("."));
            AudioCaptureConfiguration c = new AudioCaptureConfiguration(newId,Integer.parseInt(newOp_Mic));
            return c;
        }
        return null;
    }

    @Override
    public void cancelRecording() {
        wr.CandelRecord();
    }

    @Override
    public void pauseRecording() {
        wr.PauseRecord();
    }

    @Override
    public void resumeRecording() {
        wr.ResumeRecod();
    }
    
}
