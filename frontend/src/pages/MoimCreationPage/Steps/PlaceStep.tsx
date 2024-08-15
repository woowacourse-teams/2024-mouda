import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelRadioCardGroup from '@_components/Funnel/FunnelRadioCardGroup/FunnelRadioCardGroup';

const options = [
  {
    title: '오프라인',
    description: '농구, 간단한 맥주, 커피챗 등 집 밖에서 친구들을 만나보세요!',
    isSelected: true,
  },
  {
    title: '온라인',
    description: '칼바람 나락, 디스코드, 줌 등 온라인으로 친구들을 만나보세요',
    isSelected: false,
  },
];

export default function PlaceStep() {
  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Highlight>어디</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>서 </FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>모이나요?</FunnelQuestion.Text>
      </FunnelQuestion>
      <FunnelRadioCardGroup>
        {options.map((option) => (
          <FunnelRadioCardGroup.Option
            key={option.title}
            title={option.title}
            description={option.description}
            isSelected={option.isSelected}
            onSelect={() => {}}
          />
        ))}
      </FunnelRadioCardGroup>
    </>
  );
}
