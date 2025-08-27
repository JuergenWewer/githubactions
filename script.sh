for br in $(git for-each-ref --format='%(refname:short)' refs/heads); do
  git push github "$br:$br" || break
done

git ls-remote --heads github