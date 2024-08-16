import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import { css, Theme, useTheme } from '@emotion/react';

interface DescriptionStepProps {
  description: string;
  onDescriptionChange: (description: string) => void;
}

export default function DescriptionStep(props: DescriptionStepProps) {
  const { description, onDescriptionChange } = props;

  const theme = useTheme();

  return (
    <>
      <FunnelQuestion>
        <FunnelQuestion.Text>모임에 대해</FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Highlight>설명하고 싶은 내용</FunnelQuestion.Highlight>
        <FunnelQuestion.Text>이</FunnelQuestion.Text>
        <br />
        <FunnelQuestion.Text>있다면 적어주세요!</FunnelQuestion.Text>
      </FunnelQuestion>
      <textarea
        css={textArea({ theme })}
        placeholder={'칼바람 한 판 하쉴?'}
        value={description}
        onChange={(e) => onDescriptionChange(e.target.value)}
      />
    </>
  );
}

export const textArea = (props: { theme: Theme }) => css`
  ${props.theme.typography.b3}
  resize: none;

  flex-shrink: 0;

  box-sizing: border-box;
  padding: 0.6rem;
  width: 100%;
  height: 24rem;

  font-size: 1.6rem;

  background: ${props.theme.colorPalette.grey[100]};
  border: 1px solid ${props.theme.colorPalette.grey[300]};
  border-radius: 0.8rem;
`;
