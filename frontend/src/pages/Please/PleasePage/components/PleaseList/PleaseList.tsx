import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import PleaseCardList from '@_pages/Please/PleasePage/components/PleaseList/PleaseCardList/PleaseCardList';
import usePleases from '@_hooks/queries/usePleases';
import PleaseCardListSkeleton from './PleaseCardListSkeleton/PleaseCardListSkeleton';

export default function PleaseList() {
  const { pleases, isLoading } = usePleases();

  if (isLoading) {
    return <PleaseCardListSkeleton />;
  }

  return pleases && pleases.length > 0 ? (
    <PleaseCardList pleases={pleases} />
  ) : (
    <MissingFallback text="아직 해주세요가 없습니다" />
  );
}
