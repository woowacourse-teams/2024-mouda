import MoimCard from '../Card/MoimCard';
import * as S from './MoimCardList.style';
import MoimCardListProps from './MoimCardList.type';

export default function MoimCardList(props: MoimCardListProps) {
  const { data } = props;
  return (
    <div css={S.cardListSection}>
      {data.map((moimInfo, index) => {
        return <MoimCard key={`${moimInfo.title}${index}`} data={moimInfo} />;
      })}
    </div>
  );
}
