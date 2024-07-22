import * as S from './MoimDescription.style';

interface MoimDescriptionProps {
  description: string;
}

export default function MoimDescription(props: MoimDescriptionProps) {
  const { description } = props;

  if (description === '') {
    return;
  }

  return (
    <div css={S.containerStyle}>
      <h2 css={S.titleStyle}>상세설명</h2>
      <p css={S.descriptionStyle}>{description}</p>
    </div>
  );
}
