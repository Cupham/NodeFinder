package jaist.ac.jp.NodeFinder.echonet.object.av;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.ObjectData;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.App;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.AudioInput;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eAudio extends eDataObject{
	private static final Logger logger = Logger.getLogger(eAudio.class.getName());
	private Timer timer;
	private int volumeSetting;
	private boolean muteSetting;
	private AudioInput inputSourceSetting;
	
	

	public eAudio(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x06);
		this.setClassCode((byte) 0x03);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.Audio);
	}
	
	// Provided Services	
	public boolean setVolume(int volumePercetage) {
		boolean rs = false;
		if(getVolumeSetting() == volumePercetage) {
			logger.info(String.format("Volume is already set to %d percentage! Nothing todo!",this.getVolumeSetting()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB8, new ObjectData((byte) volumePercetage))) {
				refreshVolumeSetting(volumePercetage);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	public boolean setMute(boolean mute) {
		boolean rs = false;
		if(isMuteSetting() == mute) {
			logger.info(String.format("MuteSetting is already set to %s! Nothing todo!",isMuteSetting()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB9, new ObjectData(mute?(byte)0x30:(byte)0x31))) {
				refreshMuteSetting(mute);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshVolumeSetting(int volume) {
		if(volume != this.getVolumeSetting()) {
			logger.info(String.format("VolumeSetting changed from %d to %d",getVolumeSetting(),volume));
			setVolume(volume);
			//TODO: More task can be added here
		}
	}
	public void	refreshMuteSetting(boolean mute) {
		if(mute != this.isMuteSetting()) {
			logger.info(String.format("MuteSetting changed from %s to %s",getVolumeSetting(),mute));
			setMuteSetting(mute);
			//TODO: More task can be added here
		}
	}
	public void	refreshInputSourceSetting(AudioInput inputSource) {
		if(!inputSource.equals(this.getInputSourceSetting())) {
			logger.info(String.format("InputSourceSetting changed from %s to %s",getInputSourceSetting(),inputSource));
			setInputSourceSetting(inputSource);
			//TODO: More task can be added here
		}
	}
	
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		observeSpecificData(service);
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	
	
 	private void getSpecificData(Service service){
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xB8);
		epcs.add(EPC.xB9);
		epcs.add(EPC.xBC);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB8:
							refreshVolumeSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB8, EDT: 0x%02X}=={VolumeSetting:%d percent}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getVolumeSetting()));
						break;
						case xB9:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshMuteSetting(true);
							} else {
								refreshMuteSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB9, EDT: 0x%02X}=={MuteSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isMuteSetting()));
						break;
						case xBC:
							AudioInput input = AudioInput.fromCode(resultData.toBytes()[0]);
							if(input!= null) {
								refreshInputSourceSetting(input);
							} else {
								refreshInputSourceSetting(AudioInput.Others);
							}	
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBC, EDT: 0x%02X}=={InputSourceSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getInputSourceSetting()));
						break;
		
						default:
							break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE, ex.toString());
		}
	}

	@Override
	public void observeSpecificData(Service service) {
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xB8);
		epcs.add(EPC.xB9);
		epcs.add(EPC.xBC);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB8:
						refreshVolumeSetting(EchonetDataConverter.dataToInteger(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB8, EDT: 0x%02X}=={VolumeSetting:%d percent}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getVolumeSetting()));
					break;
					case xB9:
						if(EchonetDataConverter.dataToInteger(resultData) == 48) {
							refreshMuteSetting(true);
						} else {
							refreshMuteSetting(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB9, EDT: 0x%02X}=={MuteSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isMuteSetting()));
					break;
					case xBC:
						AudioInput input = AudioInput.fromCode(resultData.toBytes()[0]);
						if(input!= null) {
							refreshInputSourceSetting(input);
						} else {
							refreshInputSourceSetting(AudioInput.Others);
						}	
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xBC, EDT: 0x%02X}=={InputSourceSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getInputSourceSetting()));
					break;
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
		
	}

	public int getVolumeSetting() {
		return volumeSetting;
	}

	public void setVolumeSetting(int volumeSetting) {
		this.volumeSetting = volumeSetting;
	}

	public boolean isMuteSetting() {
		return muteSetting;
	}

	public void setMuteSetting(boolean muteSetting) {
		this.muteSetting = muteSetting;
	}

	public AudioInput getInputSourceSetting() {
		return inputSourceSetting;
	}

	public void setInputSourceSetting(AudioInput inputSourceSetting) {
		this.inputSourceSetting = inputSourceSetting;
	}


}
