import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';

interface PlaceStepProps {
  offlineOrOnline: string;
  place: string;
  onPlaceChange: (place: string) => void;
}

export default function PlaceStep(props: PlaceStepProps) {
  const { offlineOrOnline, place, onPlaceChange } = props;

  if (offlineOrOnline === 'online') {
    return (
      <>
        <FunnelQuestion>
          <FunnelQuestion.Highlight>온라인 링크</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>를</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Text>작성해주세요!</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput
          value={place}
          onChange={(e) => onPlaceChange(e.target.value)}
          placeholder="ex. URL, 칼바람 나락 ..."
        />
      </>
    );
  }

  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Highlight>장소명</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>을</FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>작성해주세요!</FunnelQuestion.Text>
      </FunnelQuestion>
      <FunnelInput
        value={place}
        onChange={(e) => onPlaceChange(e.target.value)}
        placeholder="주소를 입력해주세요"
      />
    </>
  );
}
